/* ---------- DATABASE ---------- */

let users = [];

/*
User structure

{
 username,
 password,
 balance,
 transactions:[]
}
*/

let currentUser = null;

/* ---------- PAGE ROUTER ---------- */

function showPage(page) {

    if (page === "home") {
        document.getElementById("content").innerHTML = `
        <div class="card">
            <h2>Welcome to Our Bank</h2>
            <p>Secure • Reliable • Modern Banking</p>
        </div>
        `;
    }

    else if (page === "about") {
        document.getElementById("content").innerHTML = `
        <div class="card">
            <h2>About Us</h2>
            <p>Our bank provides secure digital banking services.</p>
            <p>Trusted by thousands of customers across India.</p>
        </div>
        `;
    }

    else if (page === "services") {
        document.getElementById("content").innerHTML = `
        <div class="card">

        <h2>Banking Services</h2>

        <select onchange="navigateService(this.value)">
            <option>Select Service</option>
            <option value="loan">Loan Calculator</option>
            <option value="deposit">Fixed Deposit</option>
        </select>

        <div id="serviceContent"></div>

        </div>
        `;
    }

    else if (page === "netbanking") {
        showLogin();
    }

    else if (page === "contact") {
        document.getElementById("content").innerHTML = `
        <div class="card">

        <h2>Contact Us</h2>

        <input id="cname" placeholder="Your Name">
        <input id="cemail" placeholder="Email">
        <textarea id="cmsg" placeholder="Message"></textarea>

        <button onclick="submitContact()">Send</button>

        <p id="contactResult"></p>

        </div>
        `;
    }
}

/* ---------- SERVICES ---------- */

function navigateService(service) {

    let container = document.getElementById("serviceContent");

    if (service === "loan") {

        container.innerHTML = `
        <div class="card">

        <h3>Loan EMI Calculator</h3>

        <input type="number" id="loanAmount" placeholder="Loan Amount">
        <input type="number" id="loanRate" placeholder="Interest Rate">
        <input type="number" id="loanYears" placeholder="Years">

        <button onclick="calculateEMI()">Calculate EMI</button>

        <p id="emiResult"></p>

        </div>
        `;
    }

    if (service === "deposit") {

        container.innerHTML = `
        <div class="card">

        <h3>Fixed Deposit Calculator</h3>

        <input type="number" id="depositAmount" placeholder="Deposit Amount">
        <input type="number" id="depositYears" placeholder="Years">

        <button onclick="calculateDeposit()">Calculate</button>

        <p id="depositResult"></p>

        </div>
        `;
    }
}

/* ---------- EMI ---------- */

function calculateEMI() {

    let P = parseFloat(document.getElementById("loanAmount").value);
    let R = parseFloat(document.getElementById("loanRate").value) / 12 / 100;
    let N = parseFloat(document.getElementById("loanYears").value) * 12;

    if (!P || !R || !N) {
        document.getElementById("emiResult").innerHTML = "Enter valid values";
        return;
    }

    let EMI = (P * R * Math.pow(1 + R, N)) /
              (Math.pow(1 + R, N) - 1);

    document.getElementById("emiResult").innerHTML =
        "Monthly EMI: ₹ " + EMI.toFixed(2);
}

/* ---------- FIXED DEPOSIT ---------- */

function calculateDeposit() {

    let amount = parseFloat(document.getElementById("depositAmount").value);
    let years = parseFloat(document.getElementById("depositYears").value);

    let rate = 0.07;

    let maturity = amount * Math.pow(1 + rate, years);

    document.getElementById("depositResult").innerHTML =
        "Maturity Amount: ₹ " + maturity.toFixed(2);
}

/* ---------- CONTACT ---------- */

function submitContact() {

    let name = document.getElementById("cname").value;
    let email = document.getElementById("cemail").value;
    let msg = document.getElementById("cmsg").value;

    if (!name || !email || !msg) {
        document.getElementById("contactResult").innerHTML =
            "All fields required";
        return;
    }

    document.getElementById("contactResult").innerHTML =
        "Message Sent Successfully!";
}

/* ---------- LOGIN SYSTEM ---------- */

function showLogin() {

    document.getElementById("content").innerHTML = `

    <div class="card">

    <h2>Login</h2>

    <input id="loginUser" placeholder="Username">
    <input id="loginPass" type="password" placeholder="Password">

    <button onclick="login()">Login</button>

    <p>New user?
    <span onclick="showSignup()" style="color:blue;cursor:pointer;">
    Sign Up
    </span>
    </p>

    <p id="loginMsg"></p>

    </div>
    `;
}

function showSignup() {

    document.getElementById("content").innerHTML = `

    <div class="card">

    <h2>Sign Up</h2>

    <input id="newUser" placeholder="Username">
    <input id="newPass" type="password" placeholder="Password">

    <button onclick="register()">Register</button>

    <p>
    Already have account?
    <span onclick="showLogin()" style="color:blue;cursor:pointer;">
    Login
    </span>
    </p>

    </div>
    `;
}

function register() {

    let user = document.getElementById("newUser").value;
    let pass = document.getElementById("newPass").value;

    if (!user || !pass) {
        alert("Fill all fields");
        return;
    }

    users.push({
        username: user,
        password: pass,
        balance: 50000,
        transactions: []
    });

    alert("Registration Successful");

    showLogin();
}

function login() {

    let user = document.getElementById("loginUser").value;
    let pass = document.getElementById("loginPass").value;

    let found = users.find(u =>
        u.username === user && u.password === pass
    );

    if (!found) {
        document.getElementById("loginMsg").innerHTML = "Invalid login";
        return;
    }

    currentUser = found;

    showDashboard();
}

/* ---------- DASHBOARD ---------- */

function showDashboard() {

document.getElementById("content").innerHTML = `

<div class="card">

<h2>Welcome ${currentUser.username}</h2>

<h3>Balance: ₹${currentUser.balance}</h3>

<button onclick="deposit()">Deposit</button>
<button onclick="withdraw()">Withdraw</button>
<button onclick="transfer()">Transfer</button>
<button onclick="showTransactions()">Transactions</button>
<button onclick="logout()">Logout</button>

</div>

<div id="bankAction"></div>

`;

}

/* ---------- DEPOSIT ---------- */

function deposit() {

document.getElementById("bankAction").innerHTML = `

<div class="card">

<h3>Deposit Money</h3>

<input id="depositMoney" placeholder="Amount">

<button onclick="confirmDeposit()">Deposit</button>

</div>

`;

}

function confirmDeposit() {

let amount = parseFloat(document.getElementById("depositMoney").value);

currentUser.balance += amount;

currentUser.transactions.push("Deposited ₹" + amount);

showDashboard();

}

/* ---------- WITHDRAW ---------- */

function withdraw() {

document.getElementById("bankAction").innerHTML = `

<div class="card">

<h3>Withdraw Money</h3>

<input id="withdrawMoney" placeholder="Amount">

<button onclick="confirmWithdraw()">Withdraw</button>

</div>

`;

}

function confirmWithdraw() {

let amount = parseFloat(document.getElementById("withdrawMoney").value);

if (amount > currentUser.balance) {
alert("Insufficient Balance");
return;
}

currentUser.balance -= amount;

currentUser.transactions.push("Withdrawn ₹" + amount);

showDashboard();

}

/* ---------- TRANSFER ---------- */

function transfer() {

document.getElementById("bankAction").innerHTML = `

<div class="card">

<h3>Transfer Money</h3>

<input id="transferUser" placeholder="Receiver Username">
<input id="transferAmount" placeholder="Amount">

<button onclick="confirmTransfer()">Transfer</button>

</div>

`;

}

function confirmTransfer() {

let receiver = document.getElementById("transferUser").value;
let amount = parseFloat(document.getElementById("transferAmount").value);

let user = users.find(u => u.username === receiver);

if (!user) {
alert("User not found");
return;
}

if (amount > currentUser.balance) {
alert("Insufficient balance");
return;
}

currentUser.balance -= amount;
user.balance += amount;

currentUser.transactions.push("Transferred ₹" + amount + " to " + receiver);

showDashboard();

}

/* ---------- TRANSACTION HISTORY ---------- */

function showTransactions() {

let list = currentUser.transactions.map(t => `<li>${t}</li>`).join("");

document.getElementById("bankAction").innerHTML = `

<div class="card">

<h3>Transaction History</h3>

<ul>${list}</ul>

</div>

`;

}

/* ---------- LOGOUT ---------- */

function logout() {
currentUser = null;
showPage("home");
}

showPage("home");