(ns napa.core
  (:use [clojure-hadoop.defjob :only (defjob)]
        clojure-hadoop.wrap
        clojure.string))

(defn my-map [key value]
  (println "map: " key value)
  (let [[md5 uuid] (split value #",")]
    [[key [md5 uuid]]]))

(defn my-reduce [key values-fn]
  (let [values (values-fn)]
    (println "reduce: " key values)
    (let [[md5 uuid] (first values)]
      [[key (format "%s\t%s" md5 uuid)]])))

(defjob job
  :map my-map
  :map-reader string-map-reader
  :reduce my-reduce
  :input-format :text)
