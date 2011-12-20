(extract
 (write-headers
  (compute-unique-inputs
   :predecessors (write-headers))
  convert-geocoding-results-to-tab
  convert-validation-json-results-to-tab
  write-uuid-retention-mapping-to-sequence-file
  (extract-entities
   ;; Pass these input directories into extract-entities as
   ;; input{1,2,3}?
   :predecessors (convert-validation-json-results-to-tab
                  convert-geocoding-results-to-tab
                  write-uuid-retention-mapping-to-sequence-file))
  compute-sorted-unique-md5s
  analyze-extracted-entities
  post-process-extracted-data))
