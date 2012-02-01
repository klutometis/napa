(ns hew.analyze-entities
  (:import (mapreduce.tasks AnalyzeExtractedEntities))
  (:gen-class
   :implements [vineyard.hadoop.MapReduceJobCreator]))

(defn -createJob [wtf configuration]
  (.createJob (AnalyzeExtractedEntities.)))
