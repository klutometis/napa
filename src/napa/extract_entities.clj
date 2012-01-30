(ns napa.extract-entities
  (:import (mapreduce.tasks ExtractEntities))
  (:gen-class
   :implements [vineyard.hadoop.MapReduceJobCreator]))

(defn -createJob [wtf configuration]
  (.createJob (ExtractEntities.)))
