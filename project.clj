(defproject napa "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [clojure-hadoop "1.3.1-SNAPSHOT"]
                 [org.clojure/data.json "0.1.1"]
                 [cadr "1.0.1-SNAPSHOT"]
                 [lambda "1.0.2-SNAPSHOT"]
                 [factual/vineyard-hadoop "1.2.3-SNAPSHOT"]
                 ;; [factual/vineyard-java-driver "1.4.3-SNAPSHOT"]
                 [hewtoy "1.0.1-SNAPSHOT"]
                 [factual/hadoop-extraction-workflow "0.1-SNAPSHOT"]]
  :repositories {"factual" "http://maven.corp.factual.com/nexus/content/groups/public"
                 "releases" "http://maven.corp.factual.com/nexus/content/repositories/releases"
                 "snapshots" "http://maven.corp.factual.com/nexus/content/repositories/snapshots"}
  :dev-dependencies [[debug "1.0.0-SNAPSHOT"]
                     [ordered "1.0.0"]])
