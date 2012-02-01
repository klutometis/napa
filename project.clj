(defproject napa "1.0.0-SNAPSHOT"
  :description "Frontend to Vineyard for e.g. Vineyardized HEW"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [cadr "1.0.1-SNAPSHOT"]
                 [factual/vineyard-hadoop "1.2.3-SNAPSHOT"]
                 [factual/vineyard-java-driver "1.4.6-SNAPSHOT"]]
  :repositories {"factual" "http://maven.corp.factual.com/nexus/content/groups/public"
                 "releases" "http://maven.corp.factual.com/nexus/content/repositories/releases"
                 "snapshots" "http://maven.corp.factual.com/nexus/content/repositories/snapshots"}
  :dev-dependencies [[debug "1.0.1-SNAPSHOT"]
                     [add-classpath "1.0.3-SNAPSHOT"]])
