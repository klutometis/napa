(ns napa.write-uuid-retention
  (:import (mapreduce.tasks.uuidretention WriteUuidRetentionMappingToSequenceFile))
  (:gen-class
   :implements [vineyard.hadoop.MapReduceJobCreator]))

(defn -createJob [wtf configuration]
  (.createJob (WriteUuidRetentionMappingToSequenceFile.)))
