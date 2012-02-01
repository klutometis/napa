(ns napa
  (:use ))

(use 'add-classpath.core)

(add-classpath "lib/*")
(add-classpath "lib/dev/*")

(use 'debug.core)
(use 'lambda.core)
(use 'cadr.core)
(use 'clojure.test)

(import '(vineyard.hadoop MapReduceTask)
        '(vineyard.shell ShellTask)
        '(vineyard Task
                   TaskQueue)
        '(hewtoy MD5AndPayloadJoiner
                 UUIDToPayloadMapper
                 UUIDToMD5Mapper))

(defn make-map-reduce-task [name class input output]
  (doto (MapReduceTask.)
    (.setInitData "input_path" input)
    (.setInitData "output_path" output)
    (.setInitData "mr_job_name" name)
    (.setInitData "mr_job_class" (.getCanonicalName class))))

(defn make-shell-task [path]
  (doto (ShellTask.)
    (.setInitData ShellTask/SHELL_CMD_KEY path)))

(defλ tree->graph [tree]
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

#_(defλ run-tree [tree]
    (let [graph (tree->graph tree)
          tasks (map :task (topological-sort graph))]
      (.runAll (TaskQueue. *default-host* *default-port* *default-resource*)
               (car tasks))))

(import '(hewtoy UUIDToMD5Mapper
                 UUIDToMD5Mapper
                 MD5AndPayloadJoiner))

;;; These procedures are distinct from the DSL that invokes them.
(def join-md5-and-payload
  (make-map-reduce-task
   "join-md5-and-payload"
   MD5AndPayloadJoiner
   "uuid-to-payload,uuid-to-md5"
   "md5-and-payload"))

(def map-uuid-to-md5
  (make-map-reduce-task
   "map-uuid-to-md5"
   UUIDToMD5Mapper
   "md5-to-uuid"
   "uuid-to-md5"))

(def map-uuid-to-payload
  (make-map-reduce-task
   "map-uuid-to-payload"
   UUIDToPayloadMapper
   "md5-to-uuid"
   "uuid-to-payload"))

(def make-uuid-md5s
  (make-shell-task
   "./make-md5-to-uuid.sh"))

(defλ task-table [tasks]
  (reduce (λ [table task] (assoc table task (eval task)))
          {}
          tasks))

(defλ build-job [parent->children root tasks]
  (let [queue (atom (clojure.lang.PersistentQueue/EMPTY))
        visited (transient #{})]
    (swap! queue (λ [queue] (conj queue root)))
    (conj! visited root)
    (while (not (empty? (deref queue)))
      (let [parent (peek (deref queue))]
        (swap! queue (λ [queue] (pop queue)))
        (let [children (get parent->children parent)]
          (doseq [child children]
            (if (not (get visited child))
              (do
                (.addFirstly (get tasks parent)
                             (get tasks child))
                (conj! visited child)
                (swap! queue (λ [queue] (conj queue child)))))))))
    (get tasks root)))

(def ^:dynamic *default-host* "task07")
(def ^:dynamic *default-port* 8081)
(def ^:dynamic *default-resource* "test")

(let [{:keys [child->parents parent->children]}
      (tree->graph
       '(join-md5-and-payload
         (map-uuid-to-md5
          make-uuid-md5s)
         (map-uuid-to-payload
          make-uuid-md5s)))
      tasks (task-table '(join-md5-and-payload
                          map-uuid-to-md5
                          map-uuid-to-payload
                          make-uuid-md5s))]
  (let [root (build-job parent->children 'join-md5-and-payload tasks)]
    (.runAll (TaskQueue. *default-host* *default-port* *default-resource*)
             root)))
