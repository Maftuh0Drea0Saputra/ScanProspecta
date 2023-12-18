import gradio as gr
import pickle
import numpy as np
from PIL import Image
from sklearn.feature_extraction.text import TfidfVectorizer
import pytesseract

# Load your resume classifier model
with open('resume_classifier_5.pkl', 'rb') as file:
    model = pickle.load(file)

# Load your word vectorizer
with open('word_vectorizer.pkl', 'rb') as file:
    word_vectorizer = pickle.load(file)

# Define a function to preprocess text before classification
def preprocess_text(text):
    return word_vectorizer.transform(text)


# Define the function to predict top labels along with probabilities
def predict_top_labels(resume_text):
    # Preprocess the text using the loaded vectorizer
    processed_text = preprocess_text(resume_text)

    # Perform classification using your model
    probabilities = model.predict_proba(processed_text)[0]

    # Get indices of top 3 labels based on probabilities
    top_3_indices = np.argsort(probabilities)[::-1][:3]

    # Get top labels and their probabilities
    top_labels = model.classes_[top_3_indices]
    top_probabilities = probabilities[top_3_indices]

    # Create a formatted result with labels and their probabilities
    result = [f"{label}: {prob:.2f}%" for label, prob in zip(top_labels, top_probabilities * 100)]
    return result


# Define a Gradio OCR function to extract text from the uploaded image
def perform_ocr(input_image):
    # Convert Gradio image object to PIL image
    img = Image.fromarray(input_image.astype('uint8'), 'RGB')

    # Perform OCR on the image using pytesseract
    extracted_text = [pytesseract.image_to_string(img)]
    return extracted_text


# Create Gradio interface
inputs = gr.components.Image(label="Upload your resume image or document")
outputs = gr.components.Textbox(label="Top 3 Labels with Probabilities")

gr.Interface(
    fn=lambda img: predict_top_labels(perform_ocr(img)),
    inputs=inputs,
    outputs=outputs,
    title="Resume Classifier with OCR",
    description="Upload a resume image or document to classify its content and display top 3 labels with probabilities."
).launch()