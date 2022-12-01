(ns aoc-2020.day02
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-long [num]
  (Long/parseLong num))

(def demo-input "1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc")

(def real-input (slurp (io/resource "2020/day02.txt")))

(count
 (filter (fn [[_ min max char pwd]]
           (<= (parse-long min) (get (frequencies pwd) (first char) -1) (parse-long max)))
         (re-seq #"(\d+)-(\d+) (\w): (\w+)" demo-input)))
;; => 2

(count
 (filter (fn [[_ min max char pwd]]
           (<= (parse-long min) (get (frequencies pwd) (first char) -1) (parse-long max)))
         (re-seq #"(\d+)-(\d+) (\w): (\w+)" real-input)))
;; => 467


;; Part 2

(count
 (let [input (re-seq #"(\d+)-(\d+) (\w): (\w+)" demo-input)]
   (filter (fn [[_ min max char pwd]]
             (not= (= (nth pwd (dec (parse-long min))) (first char))
                   (= (nth pwd (dec (parse-long max))) (first char))))
           input)))
;; => 1

(count
 (let [input (re-seq #"(\d+)-(\d+) (\w): (\w+)" real-input)]
   (filter (fn [[_ min max char pwd]]
             (not= (= (nth pwd (dec (parse-long min))) (first char))
                   (= (nth pwd (dec (parse-long max))) (first char))))
           input)))
;; => 441
