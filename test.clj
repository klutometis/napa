(import-tasks "extract.clj")

;;; Grouping
(hadoop-extraction-workflow
  (extract
    ((dedupe
      (pre-dedupe-analysis
       pre-dedupe-analysis-summary
       generate-likely-dupe-md5s
       uniquify-likely-dupe-md5s
       compute-dedupe-uuids
       assign-uuids
       group-deduped-entities
       dedupe-qa
       remove-junk-inputs-and-entities
       perform-final-postprocessing)
      :predecessors (extract)
      :post-condition (fn [task]
                        (and (hdfs-directory-exists? "extract")
                             (some-task-predicate? task))))
     (attach-geo
      (assign-uuids-to-geocoding-results
       combine-deduped-and-geocoding-data))
     (attach-validation
      (assign-uuids-to-validation-results
       combine-dedupe-and-validation-data))
     (uuid-retention
      (compute-uuid-retention-mapping
       remove-overfolding-retained-uuids
       apply-uuid-retention-mapping
       export-data
       export-data-qa
       uuid-retention-tracker)))))
