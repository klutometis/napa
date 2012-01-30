(ns napa.convert-geocoding
  (:import (mapreduce.tasks ConvertGeocodingResultsToTab))
  (:gen-class
   :implements [vineyard.hadoop.MapReduceJobCreator]))

(defn -createJob [wtf configuration]
  (.createJob (ConvertGeocodingResultsToTab.)))
