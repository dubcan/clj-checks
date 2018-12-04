(ns misc.obdownloader
  (:require [clojure.java.io :as io]))

(def url "http://...") ;;ob url

(defn copy [uri file]
  (with-open [in (io/input-stream uri)
              out (io/output-stream file)]
    (io/copy in out)))

(doall (pmap #(let [uri (str url %)]
                (try
                  (copy uri %)
                  (catch Exception e (println (str "can't download " (.getMessage e)))))) (for [n (range 0 10)
                                                                                                extension ["jpg" "png" "gif" "jpeg" "foo" "bar" "baz"]]
                                                                                            (str (format "%05d" n) "." extension) )))
