function displayResult(data, title) {
    // 1. Convert the raw JSON string into a JS Object
    const analysis = JSON.parse(data.choices[0].message.content);

    const resultArea = document.getElementById('result');
    document.getElementById('reportTitle').innerText = title;

    // 2. Build the Dashboard HTML
    resultArea.innerHTML = `
        <div class="dashboard-header">
            <div>
                <h2 style="margin:0">${title}</h2>
                <p style="color:#64748b; margin:5px 0 0 0;">AI Generated Career Insights</p>
            </div>
            <div class="score-badge">${analysis.score}/10</div>
        </div>

        <div class="summary-box">
            <strong>Executive Summary:</strong><br>
            ${analysis.summary}
        </div>

        <div class="feedback-grid">
            <div class="card" style="border-top: 5px solid var(--success);">
                <h4 style="color:var(--success)">✅ Key Strengths</h4>
                <ul>
                    ${analysis.strengths.map(s => `<li>${s}</li>`).join('')}
                </ul>
            </div>
            <div class="card" style="border-top: 5px solid var(--danger);">
                <h4 style="color:var(--danger)">🚀 Areas to Improve</h4>
                <ul>
                    ${analysis.improvements.map(i => `<li>${i}</li>`).join('')}
                </ul>
            </div>
        </div>
    `;

    document.getElementById('resultContainer').style.display = "block";
    showLoading(false);
}