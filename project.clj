(defproject smarta-api "0.1.0-SNAPSHOT"
  :description "A lightly enhanced wrapper for MARTA's real-time API"
  :url "https://github.com/csmith-cb/smarta-api"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [metosin/compojure-api "2.0.0-alpha30"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.3.1"]
                 [ring-cors "0.1.13"]
                 [net.mikera/core.matrix "0.62.0"]
                 [clatrix "0.5.0"]
                 [clojurewerkz/support "1.1.0"]
                 [camel-snake-kebab "0.4.0"]
                 [clj-http "3.9.1"]
                 [clj-http-fake "1.0.3"]
                 [cheshire "5.8.1"]
                 [environ "1.1.0"]
                 [clj-time "0.15.0"]
                 [org.clojure/data.csv "0.1.4"]
                 [factual/geo "2.1.1"]
                 [incanter "1.9.3"]]
  :plugins [[lein-ring "0.12.5"]
            [lein-cloverage "1.0.7-SNAPSHOT"]]
  :ring {:handler smarta-api.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
