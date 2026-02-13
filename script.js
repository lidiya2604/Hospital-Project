function registerPatient(){
  let name=document.getElementById("name").value.trim();
  let email=document.getElementById("email").value.trim();
  let phone=document.getElementById("phone").value.trim();
  let pass=document.getElementById("password").value.trim();

  if(name=="" || email=="" || phone=="" || pass==""){
    alert("❌ Please fill all details!");
    return;
  }

  let patients = JSON.parse(localStorage.getItem("patients")) || [];
  patients.push({name,email,phone,pass});
  localStorage.setItem("patients", JSON.stringify(patients));

  alert("✅ Registered Successfully!");
  window.location.href="index.html";
}

function bookAppointment(){
  let pname=document.getElementById("pname").value.trim();
  let doctor=document.getElementById("doctor").value;
  let date=document.getElementById("date").value;
  let time=document.getElementById("time").value.trim();

  if(pname=="" || doctor=="" || date=="" || time==""){
    alert("❌ Please fill all appointment details!");
    return;
  }

  let apps = JSON.parse(localStorage.getItem("appointments")) || [];
  apps.push({pname,doctor,date,time,status:"Pending"});
  localStorage.setItem("appointments", JSON.stringify(apps));

  alert("✅ Appointment Booked!");
  window.location.href="appointment.html";
}

function showAppointments(){
  let apps = JSON.parse(localStorage.getItem("appointments")) || [];
  let tbody = document.getElementById("appList");
  if(!tbody) return;

  tbody.innerHTML="";

  apps.forEach((a,i)=>{
    tbody.innerHTML += `<tr>
      <td>${i+1}</td>
      <td>${a.pname}</td>
      <td>${a.doctor}</td>
      <td>${a.date}</td>
      <td>${a.time}</td>
      <td>${a.status}</td>
      <td><button onclick="cancelAppointment(${i})" class="btn btn-danger">Cancel</button></td>
    </tr>`;
  });
}

function cancelAppointment(index){
  let apps = JSON.parse(localStorage.getItem("appointments")) || [];
  apps.splice(index,1);
  localStorage.setItem("appointments", JSON.stringify(apps));
  showAppointments();
}

