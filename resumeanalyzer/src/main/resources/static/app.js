async function uploadFile() {
    const fileInput = document.getElementById('resumeFile');
    const resultDiv = document.getElementById('result');
    const resultContainer = document.getElementById('resultContainer');
    const loading = document.getElementById('loading');

    if (!fileInput.files[0]) return alert("Please select a PDF!");

    const formData = new FormData();
    formData.append("file", fileInput.files[0]);

    loading.style.display = "block";
    resultContainer.style.display = "none";

    try {
        const response = await fetch('http://localhost:8080/api/resume/upload', {
            method: 'POST',
            body: formData
        });

        const rawText = await response.text();
        const jsonResponse = JSON.parse(rawText);

        // This is the key: we dig into the JSON to get the actual message
        let mainContent = jsonResponse.choices[0].message.content;

        // Clean up the text: remove the raw ** and format it nicely
        const formattedContent = mainContent
            .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>') // Make **text** bold
            .replace(/\n/g, '<br>'); // Keep the line breaks

        resultDiv.innerHTML = `<div class="ai-feedback">${formattedContent}</div>`;
        resultContainer.style.display = "block";

    } catch (error) {
        resultDiv.innerText = "Error: " + error.message;
        resultContainer.style.display = "block";
    } finally {
        loading.style.display = "none";
    }
}