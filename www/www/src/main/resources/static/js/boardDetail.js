console.log("boardDetail.js in");

// listBtn 버튼 눌렀을때 list 페이지로 이동
document.getElementById('listBtn').addEventListener('click',()=>{
    location.href="/board/list";
})

document.getElementById('delBtn').addEventListener('click',()=>{
    document.getElementById('delForm').submit();
})

document.getElementById('modBtn').addEventListener('click',()=>{
    document.getElementById('title').readOnly=false;
    document.getElementById('content').readOnly=false;

    // <button></button>
    let modBtn = document.createElement('button');
    
    // type 설정
    modBtn.setAttribute('type', 'submit');
    modBtn.classList.add('btn','btn-outline-warning');
    modBtn.innerText="Submit";
    
    // 생성한 버튼 modForm에 추가
    document.getElementById('modForm').appendChild(modBtn);
    
    // 현재 상태에서 modBtn / delBtn 임시 삭제 처리
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();
})