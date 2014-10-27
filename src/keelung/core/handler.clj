(ns keelung.core.handler
  (:require [clojure.string :as str]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [crajure.core :as cl]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [cheshire.core :as json]
            [cider.nrepl :as cider]))

(defn search-sf-for-sale [req]
  (let [items (-> (get (:query-params req) "terms" "")
                  (str/split #","))
        cl-maps (cl/query-cl {:query (str/join " " items)
                              :section :for-sale
                              :area "sfbay"})]
    (json/encode {:items cl-maps})))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/search" [] #(search-sf-for-sale %))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
