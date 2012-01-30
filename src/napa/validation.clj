(ns napa.validation
  (:import (mapreduce.tasks ConvertValidationJsonResultsToTab))
  (:gen-class
   :implements [vineyard.hadoop.MapReduceJobCreator]))

(defn -createJob [wtf configuration]
  (.createJob (ConvertValidationJsonResultsToTab.)))