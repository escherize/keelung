(defproject keelung "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.2.0"]
                 [ring/ring-defaults "0.1.2"]
                 [crajure "0.1.0-SNAPSHOT"]
                 [cheshire "5.3.1"]]
  :plugins [[lein-ring "0.8.13"]
            [cider/cider-nrepl "0.7.0"]]
  :ring {:handler keelung.core.handler/app
         :nrepl {:start? true
                 :port 7171}}
  :profiles {:dev
             {:dependencies [[javax.servlet/servlet-api "2.5"]
                             [ring-mock "0.1.5"]]}})
