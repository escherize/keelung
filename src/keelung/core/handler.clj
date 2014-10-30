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

(defn allow-cross-origin
  "middleware function to allow crosss origin"
  [handler]
  (fn [request]
   (let [response (handler request)]
    (assoc-in response [:headers "Access-Control-Allow-Origin"]
         "*"))))

(defn options-200
  "middleware function to always 200 an OPTIONS request"
  [handler]
  (fn [request]
    (if (= :options (:request-method request))
      {:headers {"Access-Control-Allow-Origin" "*"
                 "Access-Control-Allow-Methods" "GET, POST, PUT, OPTIONS"}
       :body ""
       :status 204}
      (handler request))))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/search" [] #(search-sf-for-sale %))
  (route/not-found "Not Found"))

(def app
  (-> (wrap-defaults app-routes site-defaults)
      options-200
      allow-cross-origin))
