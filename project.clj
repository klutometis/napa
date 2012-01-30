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
                 [factual/hadoop-extraction-workflow "0.1-SNAPSHOT"]
     [factual-jAgave/factual-jAgave "1.0-2011-09-12"]
     ;; [org.clojure/clojure "1.2.0"]
     [play/play "1.2.1"]
     [commons-io/commons-io "1.4"]
     [com.google.guava/guava "10.0.1"]
     [google/gdata-core "1.0"]
     [google/gdata-spreadsheet "3.0"]
     [org.json/json "20090211"]
     [log4j/log4j "1.2.16"]
     [groovy/groovy "1.0"]
     [org.mozilla/js "1.7R3"] 
     [com.google.code.gson/gson "1.7.1"]
     [org.apache.hadoop/hadoop-core "0.20.2-cdh3u0"]
     [google/phonenumber "3.8"]
     [charts4j/charts4j "1.2"]
     [melissadata/name "2011_06_01"]
     [xerces/xercesImpl "2.9.1"]
     [xalan/xalan "2.7.1"]
     [factual/scarecrow "DEV-SNAPSHOT"]
     [org.apache.solr/solr-core "3.4.0"]
     [org.apache.solr/solr-test-framework "3.4.0"]
     [clj-cache-ehcache "0.0.4"]
     [org.yaml/snakeyaml "1.8"]
     [com.factual/geo "1.0-SNAPSHOT"]
     [com.factual/melissa-data "1.0-SNAPSHOT"]
     [com.factual/datastore-commons "1.3-SNAPSHOT"]
     ]
  :repositories {"factual" "http://maven.corp.factual.com/nexus/content/groups/public"
                 "releases" "http://maven.corp.factual.com/nexus/content/repositories/releases"
                 "snapshots" "http://maven.corp.factual.com/nexus/content/repositories/snapshots"}
  :dev-dependencies [[debug "1.0.0-SNAPSHOT"]
                     [ordered "1.0.0"]
                     [add-classpath "1.0.3-SNAPSHOT"]]
  :aot [napa.convert-geocoding
        napa.validation
        napa.write-uuid-retention
        napa.extract-entities
        napa.compute-md5s
        napa.analyze-entities
        napa.post-process]
  )
