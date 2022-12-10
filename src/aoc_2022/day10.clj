(ns aoc-2022.day10
  "🎄 Advent of Code 2022 - Day 10"
  (:require [clojure.core.match :refer [match]]
            [utils :refer [read-lines get-input]]
            [clojure.string :as str]))

(def demo-input* "addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop")
(def demo-input (read-lines demo-input*))

(def input* (get-input {:year 2022 :day 10}))
(def input (read-lines input*))

;; Part 1

(defn ->signal-strength [values x]
  (* x (nth values x)))

(defn input->values [input]
  (:values
   (reduce
    (fn [state i]
      (let [m (-> state
                  (update :values conj (:x state))
                  (update :cycle inc))]
        (match i
          ["noop"] m
          ["addx" x] (-> m
                         (update :values conj (:x state))
                         (update :x #(+ (parse-long x) %))))))
    {:cycle 1
     :x 1
     :values [0]}
    input)))

(defn part1 [input]
  (let [xs (input->values input)]
    (apply + (for [i (range 20 221 40)]
               (->signal-strength xs i)))))

(part1 demo-input)
;; => 13140

(part1 input)
;; => 13860


;; Part 2

(defn ->pixel [cycle pos]
  (let [x (- (mod (dec cycle) 40) pos)
        visible? (<= -1 x 1)]
    (if visible? \# \.)))

(defn part2 [input]
  (->> input
       input->values
       (map-indexed ->pixel)
       (partition 40)
       (map str/join)))

(part2 demo-input)
'("###..##..#..##...##.##..##..##..##..##.."
  "####..####..###...###...####..####..###."
  ".##.....#####...###.....####.....##....."
  "#####.....##.#......#####.....####......"
  ".###.##.....##.###......######......##.#"
  ".###.##.......####.#........########....")

(part2 input)
'(".###..####.#..#.####..##....##..##..###."
  ".#..#....#.#..#.#....#..#....#.#..#.#..#"
  ".#..#...#..####.###..#.......#.#....###."
  ".###...#...#..#.#....#.##....#.#....#..#"
  ".#.#..#....#..#.#....#..#.#..#.#..#.#..#"
  ".#..#.####.#..#.#.....###..##...##..###.")
