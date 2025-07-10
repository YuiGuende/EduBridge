/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function addQuestion() {
    const container = document.getElementById('questionsContainer');
    const block = document.createElement('div');
    block.className = 'question-block';
    block.innerHTML = `
        
    <div class="question-row">
        <label class="question-label">Question:</label><br> 
        <button type="button" class="remove-question-btn">Delete question</button>
    </div>
        <input type="text" class="question-text" required><br><br>
        <div class="answer-group"></div>
        <button type="button" class="add-answer-btn">Add answer</button>
        
        <hr>
    `;
    container.appendChild(block);

    block.querySelector('.add-answer-btn').onclick = () => addAnswerToQuestion(block);
    block.querySelector('.remove-question-btn').onclick = () => {
        block.remove();
        reIndexQuestions();
    };

    // Thêm mặc định 2 đáp án khi tạo mới
    addAnswerToQuestion(block);
    addAnswerToQuestion(block);

    reIndexQuestions(); // Cập nhật name
}


function addAnswerToQuestion(block) {
    const answerGroup = block.querySelector('.answer-group');
    const answerDiv = document.createElement('div');
    answerDiv.className = 'answer-item';
    answerDiv.innerHTML = `
       <i class="bi bi-dash-lg"></i><span><input type="text" class="answer-text" required></span> 
        <label>
    
            <input type="checkbox" class="answer-correct"> True
        </label>
        <button class="delete-answer" type="button" onclick="this.parentElement.remove(); reIndexQuestions()">Delete</button>
    `;
    answerGroup.appendChild(answerDiv);
    reIndexQuestions();
}


function reIndexQuestions() {
    const questionBlocks = document.querySelectorAll('.question-block');
    questionBlocks.forEach((block, qIndex) => {
        block.querySelector('.question-label').textContent = `Question ${qIndex + 1}:`;
        const questionInput = block.querySelector('.question-text');
        questionInput.setAttribute('name', `question[${qIndex}].text`);

        const answerDivs = block.querySelectorAll('.answer-item');
        answerDivs.forEach((div, aIndex) => {
            const textInput = div.querySelector('.answer-text');
            const checkbox = div.querySelector('.answer-correct');

            textInput.setAttribute('name', `question[${qIndex}].answers[${aIndex}].text`);
            console.log("index", qIndex);
            checkbox.setAttribute('name', `question[${qIndex}].answers[${aIndex}].correct`);
        });
    });
}


document.getElementById("quizForm").addEventListener("submit", function (e) {
    const blocks = document.querySelectorAll('.question-block');
    for (const block of blocks) {
        const checkboxes = block.querySelectorAll("input[type='checkbox']");
        const hasCorrect = Array.from(checkboxes).some(cb => cb.checked);
        if (!hasCorrect) {
            alert("Mỗi câu hỏi phải có ít nhất một đáp án đúng!");
            e.preventDefault();
            return;
        }
    }
});