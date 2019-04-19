{:repl {:env {:marta-api-key "some-key-here"
              :marta-api-uri "http://developer.itsmarta.com"}
        :dependencies [[org.clojure/tools.nrepl "0.2.13"]]}}
{:dev {:resource-paths ["test/resources"]
       :plugins [[codox "0.10.0"]]
       :codox {:source-paths ["src/clojure"]}
       :env {:marta-api-key "some-key-here"
             :marta-api-uri "http://developer.itsmarta.com"}}}
