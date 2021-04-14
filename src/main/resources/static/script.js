function addImage() {
  let url = document.getElementById('endereco_imagem').value;
  let lista = document.getElementById('lista-imagens');
  
  let s = `<li class="list-group-item d-flex justify-content-between align-items-center" style="overflow: auto">`
                +url+
                `<input name="imagem" type="hidden" value="` + url + `"><button type="button" class="btn btn-danger sm" onclick="$(this).closest('li').remove();">Remover</button>
              </li>`;

  lista.insertAdjacentHTML("beforeend", s);
  
  document.getElementById('endereco_imagem').value='';
  
}

function addPR() {
  let pergunta = document.getElementById('pergunta').value;
  let resposta = document.getElementById('resposta').value;
  let lista = document.getElementById('lista-pr');
  
  let s = `<li class="list-group-item d-flex justify-content-between align-items-center" style="padding-left: 0px">
                <div>
                  <p class="font-weight-normal">` + pergunta + `</p>
                  <input name="pergunta" type="hidden" value="` + pergunta + `">
                  <p class="font-weight-light" name="resposta">` + resposta + `</p>
                  <input name="resposta" type="hidden" value="` + resposta + `">
                </div>
                <button type="button" class="btn btn-danger sm" onclick="$(this).closest('li').remove();">Remover</button>
              </li>`;

  lista.insertAdjacentHTML("beforeend", s);
  
  document.getElementById('pergunta').value='';
   document.getElementById('resposta').value='';
}

function visualizarProduto(){
  
}


function fMasc(objeto, mascara) {
  obj = objeto
  masc = mascara
  setTimeout("fMascEx()", 1)
}
function fMascEx() {
  obj.value = masc(obj.value)
}
function mTel(tel) {
  tel = tel.replace(/\D/g, "")
  tel = tel.replace(/^(\d)/, "($1")
  tel = tel.replace(/(.{3})(\d)/, "$1)$2")
  if (tel.length == 9) {
    tel = tel.replace(/(.{1})$/, "-$1")
  } else if (tel.length == 10) {
    tel = tel.replace(/(.{2})$/, "-$1")
  } else if (tel.length == 11) {
    tel = tel.replace(/(.{3})$/, "-$1")
  } else if (tel.length == 12) {
    tel = tel.replace(/(.{4})$/, "-$1")
  } else if (tel.length > 12) {
    tel = tel.replace(/(.{4})$/, "-$1")
  }
  return tel;
}
function mCNPJ(cnpj) {
  cnpj = cnpj.replace(/\D/g, "")
  cnpj = cnpj.replace(/^(\d{2})(\d)/, "$1.$2")
  cnpj = cnpj.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3")
  cnpj = cnpj.replace(/\.(\d{3})(\d)/, ".$1/$2")
  cnpj = cnpj.replace(/(\d{4})(\d)/, "$1-$2")
  return cnpj
}
function mCPF(cpf) {
  cpf = cpf.replace(/\D/g, "")
  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2")
  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2")
  cpf = cpf.replace(/(\d{3})(\d{1,2})$/, "$1-$2")
  return cpf
}
function mCEP(cep) {
  cep = cep.replace(/\D/g, "")
  cep = cep.replace(/^(\d{2})(\d)/, "$1.$2")
  cep = cep.replace(/\.(\d{3})(\d)/, ".$1-$2")
  return cep
}
function mNum(num) {
  num = num.replace(/\D/g, "")
  return num
}
