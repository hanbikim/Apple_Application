#run FLASK_APP=app.py flask run --host=0.0.0.0 --port=5002
from flask import Flask, render_template, url_for

app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def uploadImg():
    return "Top Gun"

@app.route('/showImg')
def showImg():
    return render_template('showImg.html')

if __name__ == "__main__":
    app.run(host = '0.0.0.0', port ='5002', debug=True) 
    # host = '0.0.0.0' not local