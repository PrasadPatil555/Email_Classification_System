//// content.js
//
//// Function to inject classify button inside Gmail email view
//function injectClassifyButton() {
//    const emailBody = document.querySelector('.a3s'); // Gmail email body container
//    if (!emailBody) return;
//
//    // Avoid duplicating button
//    if (document.getElementById('classifyBtn')) return;
//
//    // Create button
//    const btn = document.createElement('button');
//    btn.id = 'classifyBtn';
//    btn.innerText = 'Classify Email';
//    btn.style.cssText = `
//        background:#007bff;
//        color:white;
//        padding:8px 12px;
//        border:none;
//        border-radius:6px;
//        cursor:pointer;
//        margin:10px 0;
//        font-size:14px;
//        font-weight:bold;
//        box-shadow: 0 2px 6px rgba(0,0,0,0.15);
//    `;
//
//    // Append button to email body
//    emailBody.appendChild(btn);
//
//    // On button click → send email content to backend
//    btn.addEventListener('click', () => classifyEmail(emailBody.innerText));
//}
//
//// Function to send email content to backend and show category
//function classifyEmail(content) {
//    fetch('http://localhost:8080/api/email/classify', {
//        method: 'POST',
//        headers: { 'Content-Type': 'application/json' },
//        body: JSON.stringify({ content })
//    })
//    .then(response => response.json())
//    .then(data => {
//        alert(`Category: ${data.category}`);
//    })
//    .catch(err => {
//        console.error('Error:', err);
//        alert('Failed to classify email. Check if backend is running.');
//    });
//}
//
//// Gmail loads dynamically, so check every 2 seconds if an email is open
//setInterval(injectClassifyButton, 2000);
// content.js

// Function to inject classify button inside Gmail email view
function injectClassifyButton() {
    const emailBody = document.querySelector('.a3s'); // Gmail email body container
    if (!emailBody) return;

    if (document.getElementById('classifyBtn')) return; // Avoid duplicates

    const btn = document.createElement('button');
    btn.id = 'classifyBtn';
    btn.innerText = 'Classify Email';
    btn.style.cssText = `
        background:#007bff;
        color:white;
        padding:8px 12px;
        border:none;
        border-radius:6px;
        cursor:pointer;
        margin:10px 0;
        font-size:14px;
        font-weight:bold;
        box-shadow: 0 2px 6px rgba(0,0,0,0.15);
    `;

    emailBody.appendChild(btn);

    btn.addEventListener('click', () => {
        let rawContent = emailBody.innerText || "";
        let cleanedContent = rawContent.replace(/\s+/g, " ").trim();

        if (!cleanedContent) {
            alert("Email content is empty!");
            return;
        }

        classifyEmail(cleanedContent);
    });
}

function classifyEmail(content) {
    fetch("http://localhost:8080/api/email/debug", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ content })
    })
    .then(response => response.json())
    .then(debugData => {
        console.log("Debug response:", debugData);
        // Now send to actual classifier
        return fetch("http://localhost:8080/api/email/classify", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ content })
        });
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Server returned ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        alert(`Category: ${data.category}`);
    })
    .catch(err => {
        console.error("Error:", err);
        alert("Failed to classify email. Check backend logs.");
    });
}

setInterval(injectClassifyButton, 2000);
