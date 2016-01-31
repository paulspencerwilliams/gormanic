(ns gormanic.core-test
  (:require [clojure.test :refer :all]
            [gormanic.core :refer :all]
            [clj-time.coerce :as timec]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]))

(defn timestamp-to-date [timestamp]
  (.toLocalDate (timec/from-long (* timestamp 1000))))

(defn is2ndIntermission [d]
  (and (= 12 (.get (.monthOfYear d)))
       (= 31 (.get (.dayOfMonth d)))))

(defn is1stIntermission [d]
  (and (= 12 (.get (.monthOfYear d)))
       (= 30 (.get (.dayOfMonth d)))))

(defn equal-ignoring-leap-year-intermission [original roundtripped]
  (or (= original (.toLocalDate roundtripped))
      (and (is2ndIntermission original) (is1stIntermission roundtripped))))

(defspec dates-are-reversable 10000
         (prop/for-all
           [d (gen/fmap
                timestamp-to-date
                (gen/choose -17987443200 19880899200))]
           (equal-ignoring-leap-year-intermission
             d (gormanic-to-gregorian (gregorian-to-gormanic d)))))