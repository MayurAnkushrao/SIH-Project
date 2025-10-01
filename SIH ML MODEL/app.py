# from flask import Flask, request, jsonify
# import pickle
# import traceback

# app = Flask(__name__)

# # ---------------------------
# # Load model and vectorizer
# # ---------------------------
# with open("model.pkl", "rb") as f:
#     model = pickle.load(f)

# with open("vectorizer.pkl", "rb") as f:
#     vectorizer = pickle.load(f)


# # ---------------------------
# # Health-check endpoint
# # ---------------------------
# @app.route("/", methods=["GET"])
# def home():
#     return jsonify({"status": "ok", "message": "Sentiment API is running 🚀"}), 200


# # ---------------------------
# # Prediction endpoint
# # ---------------------------
# @app.route("/predict", methods=["POST"])
# def predict():
#     try:
#         data = request.get_json(force=True)
#         text = data.get("text", "")

#         transformed_text = vectorizer.transform([text])
#         prediction = model.predict(transformed_text)[0]
#         proba = model.predict_proba(transformed_text).max()

#         # Convert prediction to label text
#         if str(prediction) == "1":
#             label_text = "positive"
#         else:
#             label_text = "negative"

#         # Convert confidence to percentage
#         confidence_pct = proba * 100

#         # Determine confidence category as per ranges
#         if 50 <= confidence_pct <= 76:
#             confidence_category = "neutral positive"
#         elif 24 <= confidence_pct < 50:
#             confidence_category = "neutral negative"
#         else:
#             confidence_category = label_text

#         return jsonify({
#             "confidence": proba,
#             "input_text": text,
#             "predicted_label": label_text,
#             "confidence_category": confidence_category
#         })
#     except Exception as e:
#         return jsonify({
#             "error": str(e),
#             "trace": traceback.format_exc()
#         }), 400


# if __name__ == "__main__":
#     app.run(host="127.0.0.1", port=5000, debug=True)
from flask import Flask, request, jsonify
import pickle
import traceback

app = Flask(__name__)

# Load sentiment model and vectorizer
with open("model.pkl", "rb") as f:
    model = pickle.load(f)

with open("vectorizer.pkl", "rb") as f:
    vectorizer = pickle.load(f)

# Load summarizer pipeline
with open("summarizer_pipeline.pkl", "rb") as f:
    summarizer = pickle.load(f)

# Health-check endpoint
@app.route("/", methods=["GET"])
def home():
    return jsonify({"status": "ok", "message": "API is running 🚀"}), 200

# Sentiment prediction endpoint
@app.route("/predict", methods=["POST"])
def predict():
    try:
        data = request.get_json(force=True)
        text = data.get("text", "")

        transformed_text = vectorizer.transform([text])
        prediction = model.predict(transformed_text)[0]
        proba = model.predict_proba(transformed_text).max()

        label_text = "positive" if str(prediction) == "1" else "negative"
        confidence_pct = proba * 100

        if 50 <= confidence_pct <= 76:
            confidence_category = "neutral positive"
        elif 24 <= confidence_pct < 50:
            confidence_category = "neutral negative"
        else:
            confidence_category = label_text

        return jsonify({
            "confidence": proba,
            "input_text": text,
            "predicted_label": label_text,
            "confidence_category": confidence_category
        })
    except Exception as e:
        return jsonify({
            "error": str(e),
            "trace": traceback.format_exc()
        }), 400

# Summarization endpoint
@app.route("/summarize", methods=["POST"])
def summarize():
    try:
        data = request.get_json(force=True)
        text = data.get("text", "")

        # Use the summarizer pipeline to summarize text
        summary_list = summarizer(text, max_length=150, min_length=30, do_sample=False)
        summarized_text = summary_list[0]['summary_text']

        return jsonify({
            "input_text": text,
            "summary": summarized_text
        })
    except Exception as e:
        return jsonify({
            "error": str(e),
            "trace": traceback.format_exc()
        }), 400

if __name__ == "__main__":
    app.run(host="127.0.0.1", port=5000, debug=True)
