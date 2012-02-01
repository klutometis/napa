(ns napa
  (:use [debug]
        [cadr]
        [clojure.test])
  (:import (vineyard.hadoop MapReduceTask)
           (vineyard.shell ShellTask)
           (vineyard Task TaskQueue)))

(defn make-map-reduce-task [name class input output]
  (doto (MapReduceTask.)
    (.setInitData "input_path" input)
    (.setInitData "output_path" output)
    (.setInitData "mr_job_name" name)
    (.setInitData "mr_job_class" (.getCanonicalName class))))

(defn make-shell-task [path]
  (doto (ShellTask.)
    (.setInitData ShellTask/SHELL_CMD_KEY path)))

(defn tree->graph [tree]
  (let [child->parents (transient {})
        parent->children (transient {})]
    (letfn [(plumb! [parents tree]
              (if (list? tree)
                ;; This is what forbids the implicit primes: we
                ;; require a parent.
                (let [parent (car tree)
                      children (cdr tree)]
                  (doseq [child children]
                    (plumb! (cons parent parents) child)))
                (loop [child tree
                       parents parents]
                  (if (empty? parents)
                    ;; Empty parent-set for roots.
                    (assoc! child->parents
                            child
                            (get child->parents child nil))
                    (let [parent (car parents)]
                      (assoc! child->parents
                              child
                              (cons parent
                                    (get child->parents child nil)))
                      (assoc! parent->children
                              parent
                              (cons child
                                    (get parent->children parent nil)))
                      ;; Also: empty set for leaves.
                      (assoc! parent->children
                              child
                              (get parent->children child nil))
                      (recur (car parents)
                             (cdr parents)))))))]
      (plumb! nil tree)
      {:child->parents (persistent! child->parents)
       :parent->children (persistent! parent->children)})))

(defn task-table [tasks]
  (reduce (fn [table task] (assoc table task (eval task)))
          {}
          tasks))

(defn build-job [parent->children root tasks]
  (let [queue (atom (clojure.lang.PersistentQueue/EMPTY))
        visited (transient #{})]
    (swap! queue (fn [queue] (conj queue root)))
    (conj! visited root)
    (while (not (empty? (deref queue)))
      (let [parent (peek (deref queue))]
        (swap! queue (fn [queue] (pop queue)))
        (let [children (get parent->children parent)]
          (doseq [child children]
            (if (not (get visited child))
              (do
                (.addFirstly (get tasks parent)
                             (get tasks child))
                (conj! visited child)
                (swap! queue (fn [queue] (conj queue child)))))))))
    (get tasks root)))

(def ^:dynamic *default-host* "task07")
(def ^:dynamic *default-port* 8081)
(def ^:dynamic *default-resource* "test")

(defn tree->tasks [tree]
  (distinct (flatten tree)))

(defn run-tree [tree root]
  (let [{:keys [child->parents parent->children]} (tree->graph tree)
        tasks (tree->tasks tree)]
    (let [root (build-job parent->children root tasks)]
      (.runAll (TaskQueue. *default-host* *default-port* *default-resource*)
               root))))
