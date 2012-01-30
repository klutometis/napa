(ns napa.compute-md5s
  (:import (mapreduce.tasks ComputeSortedUniqueMd5s))
  (:gen-class
   :implements [vineyard.hadoop.MapReduceJobCreator]))

(defn -createJob [wtf configuration]
  (.createJob (ComputeSortedUniqueMd5s.)))
