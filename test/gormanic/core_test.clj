(ns gormanic.core-test
  (:require [clojure.test :refer :all]
            [gormanic.core :refer :all]
            [clj-time.core :as t]))

(deftest output-tests
  (testing "pre leap year"
    (is (= "1 March 2016" (gregorian-to-gormanic-string (t/date-time 2016 1 1))))
    (is (= "1 April 2016" (gregorian-to-gormanic-string (t/date-time 2016 1 29))))
    (is (= "2 April 2016" (gregorian-to-gormanic-string (t/date-time 2016 1 30))))
    (is (= "6 May 2016" (gregorian-to-gormanic-string (t/date-time 2016 3 2)))))
  (testing "non leap year"
    (is (= "1 April 2015" (gregorian-to-gormanic-string (t/date-time 2015 1 29))))
    (is (= "5 May 2015" (gregorian-to-gormanic-string (t/date-time 2015 3 2))))
    (is (= "1 February 2015" (gregorian-to-gormanic-string (t/date-time 2015 11 5))))
    (is (= "7 June 2015" (gregorian-to-gormanic-string (t/date-time 2015 4 1))))
    (is (= "28 Gormanuary 2015" (gregorian-to-gormanic-string (t/date-time 2015 12 30))))  
    (is (= "13 Gormanuary 2015" (gregorian-to-gormanic-string (t/date-time 2015 12 15))))  
    (is (= "Intermission 2015" (gregorian-to-gormanic-string (t/date-time 2015 12 31))))
    (is (= "Intermission 2015" (gregorian-to-gormanic-string (t/date-time 2015 12 31)))))
  (testing "post leap year")
  (is (= "4 May 2016" (gregorian-to-gormanic-string (t/date-time 2016 2 29))))  
  (is (= "2 February 2016" (gregorian-to-gormanic-string (t/date-time 2016 11 5))))  
  (is (= "8 June 2016" (gregorian-to-gormanic-string (t/date-time 2016 4 1))))  
  (is (= "Intermission 2016" (gregorian-to-gormanic-string (t/date-time 2016 12 30))))  
  (is (= "Intermission 2016" (gregorian-to-gormanic-string (t/date-time 2016 12 31)))))