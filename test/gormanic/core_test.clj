(ns gormanic.core-test
  (:require [clojure.test :refer :all]
            [gormanic.core :refer :all]
            [clj-time.coerce :as timec]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clj-time.core :as t]))

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

(defspec dates-are-roundtrippable 10000
         (prop/for-all
           [d (gen/fmap
                timestamp-to-date
                (gen/choose -17987443200 19880899200))]
           (equal-ignoring-leap-year-intermission
             d (to-gregorian (to-gormanic d)))))

(deftest output-tests
  (testing "pre leap year"
    (is (= "1 March 2016" (to-gormanic-string (t/date-time 2016 1 1)))))
  (testing "post leap year"
    (is (= "8 June 2016" (to-gormanic-string (t/date-time 2016 4 1))))  
    (is (= "Intermission 2016" (to-gormanic-string (t/date-time 2016 12 30))))  
    (is (= "Intermission 2016" (to-gormanic-string (t/date-time 2016 12 31)))))
  (testing "non leap year"
    (is (= "1 April 2015" (to-gormanic-string (t/date-time 2015 1 29))))
    (is (= "28 Gormanuary 2015" (to-gormanic-string (t/date-time 2015 12 30))))
    (is (= "Intermission 2015" (to-gormanic-string (t/date-time 2015 12 31))))))
