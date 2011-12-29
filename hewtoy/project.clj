(defproject hewtoy "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [
                 ;; [org.clojure/clojure "1.3.0"]
                 [factual/vineyard_hadoop "1.1"]
                 [org.apache.hadoop/hadoop-core "1.0.0"]
                 ]
  :repositories {"factual" "http://maven.corp.factual.com/nexus/content/groups/public"
                 "releases" "http://maven.corp.factual.com/nexus/content/repositories/releases"
                 "snapshots" "http://maven.corp.factual.com/nexus/content/repositories/snapshots"}
  :source-path "src/clojure"
  :java-source-path "src/java")
