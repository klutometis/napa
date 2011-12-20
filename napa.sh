# napa <task-spec.clj> task [options]*

# Overriding tagged input-paths
napa dedupe.clj dedupe --input-paths input:input1,input:input2

# Overriding untagged input-paths
napa dedupe.clj dedupe --input-paths input1,input2

# Defining a function
napa dedupe.clj dedupe --initialize '(fn [task] (set-property! task ...))'

# Passing in a map
napa dedupe.clj dedupe --initialize '{ :reduce 22, :mappers (m1, m2) }'

# Ad-hoc bash-map
napa dedupe.clj dedupe --initialize reduce:22,mappers:m1,m2
