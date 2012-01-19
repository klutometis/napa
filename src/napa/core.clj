(ns napa.core
  (:import (mapreduce.tasks ConvertGeocodingResultsToTab)
           (vineyard.hadoop MapReduceJobCreator
                            MapReduceTask)
           (vineyard Task
                     TaskQueue)
           (org.apache.hadoop.conf Configuration)
           (org.apache.hadoop.mapreduce Job))
  (:use [debug.core])
  (:gen-class
   :implements [vineyard.hadoop.MapReduceJobCreator]
   ))

;;; Set name on each task before I submit it.
#_(def job
    (proxy [MapReduceJobCreator] []
    (createJob [configuration]
      (debug 'harro)
      (.createJob (ConvertGeocodingResultsToTab.)))))

#_(gen-class
 :name napa.core.ConvertGeocoding
 :implements [MapReduceJobCreator]
 :prefix "cg-"
 :methods [[createJob [Configuration] Job]])

#_(defn cg-createJob [configuration]
  (.createJob (ConvertGeocodingResultsToTab.)))

(defn -createJob [wtf configuration]
  (.createJob (ConvertGeocodingResultsToTab.)))
