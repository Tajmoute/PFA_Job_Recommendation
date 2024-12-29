from flask import Flask, request, jsonify
import json
import math
from sentence_transformers import SentenceTransformer

app = Flask(__name__)

# 1) Load the pre-trained model for new embeddings
model = SentenceTransformer('all-MiniLM-L6-v2')

# 2) Load the job embeddings from job_embeddings.jsonl into a dictionary
job_embeddings = {}  # { "uniq_id": [float, float, ...], ... }

def load_job_embeddings(jsonl_file):
    with open(jsonl_file, 'r', encoding='utf-8') as f:
        for line in f:
            record = json.loads(line.strip())
            uniq_id = record['uniq_id']
            embedding = record['embedding']  # a list of floats
            job_embeddings[uniq_id] = embedding

def cosine_similarity(vecA, vecB):
    dot_product = 0.0
    normA = 0.0
    normB = 0.0
    for a, b in zip(vecA, vecB):
        dot_product += a * b
        normA += a*a
        normB += b*b
    return dot_product / ((normA**0.5) * (normB**0.5))

@app.route('/recommend', methods=['POST'])
def recommend():
    """
    Expects a JSON payload: { "skills": "Java Spring Microservices" }
    Returns top N recommended job uniq_ids based on similarity.
    """
    data = request.json
    skills_text = data.get('skills', '')  # e.g. "Java Spring Microservices"
    if not skills_text:
        return jsonify({"error": "No skills provided"}), 400

    # 1) Embed the user's skill text
    user_vector = model.encode(skills_text).tolist()

    # 2) Compute similarity with each job, store results
    scored = []
    for uniq_id, emb in job_embeddings.items():
        score = cosine_similarity(user_vector, emb)
        scored.append((uniq_id, score))

    # 3) Sort by descending similarity
    scored.sort(key=lambda x: x[1], reverse=True)

    # 4) Take top 10 (or however many you want)
    top_20 = scored[:20]

    # 5) Return a JSON response with the ranked job ids
    # Example output: { "recommendations": [ {"uniq_id": "...", "score": 0.98}, ... ] }
    recommendations = [
        {"uniq_id": item[0], "score": item[1]} 
        for item in top_20
    ]
    return jsonify({"recommendations": recommendations})

if __name__ == "__main__":
    # Load embeddings once at startup
    load_job_embeddings("job_embeddings.jsonl")
    
    # Run Flask app on localhost:5000 (by default)
    # or specify host='0.0.0.0' if you want external access
    app.run(port=5000, debug=True)
