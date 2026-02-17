<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Management</title>

<style>
    body {
        font-family: Arial;
        background-color: #f2f2f2;
    }

    .container {
        width: 400px;
        margin: 80px auto;
        padding: 20px;
        background: white;
        border-radius: 8px;
        box-shadow: 0px 0px 10px gray;
    }

    h2 {
        text-align: center;
    }

    label {
        font-weight: bold;
    }

    input[type=text], input[type=number] {
        width: 100%;
        padding: 8px;
        margin: 6px 0 12px 0;
        border-radius: 4px;
        border: 1px solid #ccc;
    }

    .btn {
        width: 45%;
        padding: 10px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-weight: bold;
    }

    .insertBtn {
        background-color: #4CAF50;
        color: white;
    }

    .resetBtn {
        background-color: #f44336;
        color: white;
    }

    .btn-container {
        display: flex;
        justify-content: space-between;
    }
</style>

</head>

<body>

<div class="container">
    <h2>Student Form</h2>

    <form action="insert" method="post">
        
        <label>Roll No:</label>
        <input type="number" name="rollno" required>

        <label>Name:</label>
        <input type="text" name="name" required>

        <label>Standard:</label>
        <input type="number" name="std" required>

        <label>School:</label>
        <input type="text" name="school" required>

        <div class="btn-container">
            <input type="submit" value="INSERT" class="btn insertBtn">
            <input type="reset" value="RESET" class="btn resetBtn">
        </div>

    </form>
</div>

</body>
</html>
