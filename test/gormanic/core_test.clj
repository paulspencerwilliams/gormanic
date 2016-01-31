(ns gormanic.core-test
  (:require [clojure.test :refer :all]
            [gormanic.core :refer :all]
            [clj-time.coerce :as timec]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]))

(defn timestamp-to-date [timestamp]
   (.toLocalDate(timec/from-long (* timestamp 1000) )))

(defspec dates-are-reversable 10000
  (prop/for-all [d (gen/fmap timestamp-to-date
                             (gen/choose -17987443200 19880899200))]
                (= d (.toLocalDate (gormanic-to-gregorian (gregorian-to-gormanic d))))))