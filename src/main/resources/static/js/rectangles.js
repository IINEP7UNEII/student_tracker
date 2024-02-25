fetch('/students/json')
    .then(response => response.json())
    .then(students => 
    {
        const container = document.getElementById('rectangles-container');

        students.forEach(student => 
        {
            console.log(student);
            console.log(student.height);
            let rectDiv = document.createElement('div');
            rectDiv.classList.add('student-rectangle');
            let height = student.height;
            let weight = student.weight;
            rectDiv.style.width = weight + 'px'; 
            rectDiv.style.height = height + 'px';
            rectDiv.style.borderStyle = 'solid';
            rectDiv.style.borderColor = 'white';
            rectDiv.style.margin = '10px';
            
            let nameLabel = document.createElement('h6');
            nameLabel.textContent = student.name;
            nameLabel.style.position = 'relative';
            nameLabel.style.top = '50%';
            nameLabel.style.left = '50%';
            nameLabel.style.transform = 'translate(-50%, -50%)';
            nameLabel.style.textAlign = 'center';

            rectDiv.appendChild(nameLabel);
            container.appendChild(rectDiv);
        });
    });