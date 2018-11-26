(ns misc.core)

(+ 2 2)

;; get-jars [sNum] -> [{ :name 0 :contains true} ...]
;; choose-jar [seq] -> rand * length
;; drop-jar [stackanchikiSeq choose] -> [{ :name 0 :contains true} ...]
;; check-jar [drop-stackanchik choose] -> t / f
;; check-otherjar [drop-stackanchik choose] -> t / f

;; calc %

;; jar: {:index 0-n :award t / f}

(defn vec-remove
  "remove elem in coll"
  [coll pos]
  (vec (concat (subvec coll 0 pos) (subvec coll (inc pos)))))

(defn get-doors [n]
  (vec (map (fn [n] (boolean false)) (range 0 n))))

(defn put-car-into-door [doors car-index]
  (assoc doors car-index true))

(defn drop-door [doors car-index choosed-door]
  (let [drop-candidates-indexes (filter #(and (not= car-index %) (not= choosed-door %)) (range 0 (count doors)))]
    (assoc doors (nth drop-candidates-indexes (rand-int (count drop-candidates-indexes))) nil)))

(defn check-door [doors checked-door]
  (nth doors checked-door))

(defn check-another-door [doors checked-door]
  (first (filter #(not= nil %) (vec-remove doors checked-door))))

(def doors-count 3)
(def empty-doors (get-doors doors-count))
empty-doors

(def car-index (rand-int doors-count))
car-index

(def doors (put-car-into-door empty-doors car-index))
doors

(def choosed-door (rand-int doors-count))
choosed-door

(def doors-after-drop (drop-door doors car-index choosed-door))
doors-after-drop

(check-another-door doors-after-drop choosed-door)

;;
(def res (repeatedly 100000
                        #(let [doors-count 3
                              empty-doors (get-doors doors-count)
                              car-index (rand-int doors-count)
                              doors (put-car-into-door empty-doors car-index)
                              choosed-door (rand-int doors-count)
                              doors-after-drop (drop-door doors car-index choosed-door)]
                          (check-door doors-after-drop choosed-door))))
res

(float (/ (count (filter true? res)) (count res)))
  

;;
(defn get-jars [n] (into {} (map #(vector % (hash-map :index % :award false)) (range 0 n))))

(defn put-award-into-jar [jars award-index]
  (let [award-jar (get jars award-index)]
    (assoc jars award-index {:index (:index award-jar) :award true})))

(defn choose-jar [jars] (rand-int (count jars)))

(defn drop-jar [jars award-index choose]
  (let [non-chosed (apply dissoc jars [award-index choose])]
    (dissoc jars (:index (nth (vals non-chosed) (rand-int (count non-chosed)))))))

(defn check-jar [jars choose] (:award (get jars choose)))

(defn check-another-jar [jars choose] (:award (second (first (dissoc jars choose)))))

(def result (repeatedly 100000
                        #(let [award-index (rand-int 3)
                               jars (put-award-into-jar (get-jars 3) award-index)
                               chosed-jar-index (rand-int 3)
                               dropped-jars (drop-jar jars award-index chosed-jar-index)]
                           (check-jar dropped-jars chosed-jar-index))))
result

(float (/ (count (filter true? result)) (count result)))
