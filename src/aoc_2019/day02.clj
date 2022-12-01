(ns aoc-2019.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def sample
  (-> "1,9,10,3,2,3,11,0,99,30,40,50"
      (str/split #",")
      (->> (mapv parse-long))))

(def input
  (-> "2019/day02.txt"
      io/resource
      slurp
      (str/split ,,, #",")
      (->> (mapv parse-long ,,,))))

(def num->op {1 +,
              2 *,})

;; Part 1

(defn interpreter
  [input]
  (let [num->op {1 +
                 2 *}
        ops (partition 4 input)]
    (loop [[[op in1 in2 out] & rest] ops   ; ((1 2 3 4) (2 3 4 5) (4 5 6 7))
           acc input]
      (if (= op 99)
        (first acc)
        (recur rest
               (assoc acc out
                      ((num->op op) (nth acc in1) (nth acc in2))))))))


(interpreter (assoc input 1 12 2 2))
;; => 7210630


;; Part 2

(first
 (for [noun (range 100)
       verb (range 100)
       :when (= (interpreter (assoc input 1 noun 2 verb))
                19690720)]
   (+ (* 100 noun) verb)))
;; => 3892
