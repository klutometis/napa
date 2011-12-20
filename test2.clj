(deftask <name-of-task>
  [<task_n>]+
  :keyword value)

(deftask dedupe
  pre-dedupe-analysis
  pre-dedupe-analysis-summary
  ;; :precondition (fn [vineyard] ...)
  :input-paths (extract))
