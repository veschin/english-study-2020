(ns core
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [garden.core :refer [css]]
            [hiccup.page :as hiccup :refer [html5 include-css]]))

(defn generate-page [{:keys [lesson answers]} image path]
  (spit path (html5
              [:head
               (include-css "https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css")
               [:style (map css [[:table {:margin-top "50px"
                                          :max-width "50%"
                                          :margin-left "auto"
                                          :border-collapse "separate"
                                          :border-spacing "7px 5px"
                                          :margin-right "auto"
                                          :text-align "center"}]
                                 [:button {:margin "10px"
                                           :left 0
                                           :top 0}]
                                 [:img {:margin-top "50px"
                                        :margin-left "auto"
                                        :margin-right "auto"}]
                                 [:thead {:text-align "center"
                                          :margin-bottom "100px !important"}]
                                 [:td {:padding "5px !important"
                                       :background-color "#f7f2e4"
                                       :margin "5px"}]
                                 [:h3 {:padding "10px !important"}
                                  [:a {:color "#fff"}]
                                  [:a:hover {:color "#3b3b3b"
                                             :text-decoration "none"}]]])]]
              [:body
               [:button.btn.btn-dark [:h3 [:a {:href "../index.html"} "To Home"]]]
               [:table {:cellspacing 2}
                [:thead
                 [:tr [:th {:colspan 8} [:h2 (str "Lesson " lesson)]]]
                 [:tr [:th [:h3 "Task"]] [:th {:colspan 7} [:h3 "Answers"]]]]
                (into
                 [:tbody]
                 (for [answer answers
                       [num answer] answer]
                   (into [:tr [:td num]] (for [a answer] [:td a]))))]
               [:img.rounded.img-fluid.mx-auto.d-block {:src (str "../images/" lesson ".jpeg")}]])))

(let [css (map css [[:table {:margin-top "50px"
                             :max-width "50%"
                             :margin-left "auto"
                             :margin-right "auto"
                             :text-align "center"}]
                    [:thead {:text-align "center"
                             :margin-bottom "100px !important"}]
                    [:td {:padding "5px !important"
                          :margin "5px"}]
                    [:h3 {:padding "10px !important"
                        ;;   :border-radius "5px"
                          }
                     [:a {:color "#fff"}]
                     [:a:hover {:color "#3b3b3b"
                                :text-decoration "none"}]]])
      dir (io/file "../images/")
      templates-dir "../templates/"
      images (map str (rest (file-seq dir)))
      answers (read-string (slurp "answers.edn"))
      templates (for [i (range (count images))]
                  (str templates-dir "Lesson" (inc i) ".html"))
      _ (doall (map (fn [a t i] (generate-page a t i)) answers images templates))
      head [:head
            (include-css "https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css")
            [:style css]]
      links-to-lessons (for [[i file] (map-indexed vector templates)]
                         [:a {:href (subs file 3)} (str "Lesson " (inc i))])
      wrap-link (fn [link] [:td.btn.btn-dark [:h3 link]])
      body [:table
            [:thead
             [:tr
              [:th {:colspan 2} [:h2 "Home Education"]]]]
            (into [:tbody] (for [links (partition-all 3 links-to-lessons)
                                 :let [row (into [:tr {:align "center"}] (map wrap-link links))]] row))]
      index-template (html5
                      head
                      body)]
  (spit "../index.html" index-template))


