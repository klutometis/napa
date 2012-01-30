(ns napa.post-process
  (:import (mapreduce.tasks PostProcessExtractedData))
  (:gen-class
   :implements [vineyard.hadoop.MapReduceJobCreator]))

(defn -createJob [wtf configuration]
  (.createJob (PostProcessExtractedData. "entities")))
