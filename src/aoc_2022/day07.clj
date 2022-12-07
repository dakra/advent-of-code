(ns aoc-2022.day07
  "🎄 Advent of Code 2022 - Day 7"
  (:require [clojure.core.match :refer [match]]
            [clojure.walk :as walk]
            [utils :refer [read-lines get-input]]))

(def demo-input* "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")
(def demo-input (read-lines demo-input*))

(def input* (get-input {:year 2022 :day 7}))
(def input (read-lines input*))

(defn- cd [cwd x]
  (case x
    "/" ["/"]
    ".." (pop cwd)
    (conj cwd x)))

(defn parse-input [{:keys [cwd] :as state} input]
  (match input
    ["$" "cd" dir] (update state :cwd cd dir)
    ["$" "ls"] state  ;; (assoc state :ls? true)
    ["dir" dir] state ;; (assoc-in state (concat [:fs] cwd [dir]) {})
    [fsize fname] (assoc-in state (concat [:fs] cwd [fname]) (parse-long fsize))))

(defn input->tree [input]
  (let [state {:cwd ["/"] :fs {}}]
    (->> input
         (reduce parse-input state)
         :fs)))

;; Part 1

;; Ugly atom, but I don't want to spend more time on this puzzle :D
;; It would be better to not use postwalk and create event the fs tree
;; because it's not needed but it got me the solution quick.
(def sums! (atom []))
(defn sum-dir-fn [cmp-fn limit]
  (fn sum-dir1 [x]
    (if (map? x)
      (let [sum (apply + (vals x))]
        (when (cmp-fn sum limit)
          (swap! sums! conj sum))
        sum)
      x)))

(walk/postwalk (sum-dir-fn < 100000) (input->tree demo-input))
(apply + @sums!)
;; => 95437

(reset! sums! [])
(walk/postwalk (sum-dir-fn < 100000) (input->tree input))
(apply + @sums!)
;; => 1770595

;; Part 2

(defn calc-space-needed [input]
  (- 30000000
     (- 70000000
        (walk/postwalk (sum-dir-fn < 0) (input->tree input)))))

(calc-space-needed demo-input)
;; => 8381165

(calc-space-needed input)
;; => 2143088

(reset! sums! [])
(walk/postwalk (sum-dir-fn >= (calc-space-needed demo-input)) (input->tree demo-input))
(apply min @sums!)
;; => 24933642

(reset! sums! [])
(walk/postwalk (sum-dir-fn >= (calc-space-needed input)) (input->tree input))
(apply min @sums!)
;; => 2195372
