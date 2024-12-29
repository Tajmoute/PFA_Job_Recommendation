import csv
import json
from sentence_transformers import SentenceTransformer

model = SentenceTransformer('all-MiniLM-L6-v2')

input_csv = 'cleaned_job_market_data.csv'
output_jsonl = 'job_embeddings.jsonl'

with open(input_csv, 'r', encoding='utf-8') as infile, \
     open(output_jsonl, 'w', encoding='utf-8') as outfile:

    reader = csv.DictReader(infile)
    for row in reader:
        uniq_id = row['uniq_id']
        title = row['job_title']
        desc = row['job_description']
        combined_text = f"{title}. {desc}"

        emb = model.encode(combined_text).tolist()
        record = {
            'uniq_id': uniq_id,
            'embedding': emb
        }
        outfile.write(json.dumps(record) + "\n")
