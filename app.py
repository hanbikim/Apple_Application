#run FLASK_APP=app.py flask run --host=0.0.0.0 --port=5002
from flask import Flask, render_template

app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def uploadImg():
    return "Top Gun"

if __name__ == "main":
    app.run(host = '0.0.0.0', port ='5002', debug=True) 
    # host = '0.0.0.0' not local