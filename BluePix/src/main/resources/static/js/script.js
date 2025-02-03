
console.log('script.js loaded');

window.addEventListener('load', function(e){
	console.log('Document loaded');
	init();
});


function init(){
		getPosts();

}

function getPosts() {
	let posts;
	console.log('In the init()');
	let xhr = new XMLHttpRequest();

		xhr.open('GET', 'api/posts/', true);
		
		xhr.onreadystatechange = function() {
			if (xhr.readyState === 4) {
				if (xhr.status === 200) {
					posts = JSON.parse(xhr.responseText);
					console.log(posts);
					postToDiv(posts);
				} else {
					console.error('Error: ' + xhr.status);
				}
			}
		};

		xhr.send(); // Don't forget to send the request
}
	
	
	function postToDiv(posts){
		let postDiv = document.getElementById('postListDiv');
		postDiv.textContent = '';
		
		for(let i = 0;i < posts.length; i++){
			
			let userDiv = document.createElement('div');
			userDiv.id = '';
			userDiv.textContent = posts[i].pageUser.name;
			postDiv.appendChild(userDiv);
			
			let postTitleHeader = document.createElement('h4');
			postTitleHeader.id = 'postTitleHeader';
			postTitleHeader.textContent = posts[i].title;
			postDiv.appendChild(postTitleHeader);
			
			let postDescription = document.createElement('p');
			postDescription.id = 'postDescription';
			postDescription.textContent = posts[i].description;
			postDiv.appendChild(postDescription);
			
			if(posts[i].imageUrl !== null){
			let image = document.createElement('img');
			image.id = 'imageUrl';
			image.src = posts[i].imageUrl;
			image.style = "width:400px;height:300px";
			postDiv.appendChild(image);
			}
			postDiv.appendChild(document.createElement('hr'));
		}
	}
	
		
		//let title = document.createElement('h3');
		//title.textContent = posts.title;

