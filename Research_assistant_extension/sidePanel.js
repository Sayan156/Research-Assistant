document.addEventListener("DOMContentLoaded", async () => {

    const [tab] = await chrome.tabs.query({
        active: true,
        currentWindow: true
    });

    const url = new URL(tab.url);
    const siteKey = url.origin;

    chrome.storage.local.get([siteKey], function (result) {

        if (result[siteKey]) {
            document.getElementById('notes').value = result[siteKey];
        }

    });

});
document.getElementById('summarizebtn').addEventListener('click',summarizeText)
document.getElementById('saveNotesbtn').addEventListener('click', saveNotes)

async function summarizeText() {
  try {
    const[tab] = await chrome.tabs.query({active:true , currentWindow:true})
    const[{result}]= await chrome.scripting.executeScript(
        {target : {tabId : tab.id},
        function: ()=>window.getSelection().toString()
    }
    );
    if(!result)
    {
        showResult("Please select some text first");
        return;

    }
      const response = await fetch('http://localhost:8080/api/research/process',{
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body: JSON.stringify({content : result , operation:'summarize'})
      });
      if(!response.ok){
        throw new Error(`Api Errr ${response.status}`)
      }
      const text = await response.text();
      showResult(text.replace(/\n/g,'<br>'))
  } catch (error) {
      showResult('Error '+ error.message)
  }
    
}
async function saveNotes() {

    const notes = document.getElementById('notes').value;

    const [tab] = await chrome.tabs.query({
        active: true,
        currentWindow: true
    });

    const url = new URL(tab.url);
    const siteKey = url.origin;

    chrome.storage.local.set(
        { [siteKey]: notes },
        function () {
            alert("Notes saved successfully");
        }
    );
}


function showResult(content) {

    const formattedContent = content
        .split('\n')
        .map(line => line.trim())
        .filter(line => line.length > 0)
        .map(line => {

            if (line.startsWith('* ')) {
                return `<li>${line.substring(2)}</li>`;
            }

            if (line.startsWith('- ')) {
                return `<li>${line.substring(2)}</li>`;
            }

            return `<p>${line}</p>`;
        })
        .join('');

    document.getElementById('Results').innerHTML = `
        <div class="result-item">
            <div class="result-header">
                <span>AI Summary</span>
            </div>

            <div class="result-content">
                ${formattedContent}
            </div>
        </div>
    `;
}