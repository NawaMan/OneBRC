<script>
	import NavigationBar from "$lib/components/NavigationBar.svelte";
	import ContentPage from "$lib/templates/ContentPage.svelte";
</script>

<ContentPage
	title="Conclusion">
	<div class="left">
		<ul>
			<li>One Billion Row Challenge!</li>
			<li>My solution got quite good results.</li>
			<li>My solution is also very comprehensible.
				<ul>
					<li>Make use of virtual threads to process the data concurrenly</li>
					<li>Map file content to memory</li>
					<li>Delay string conversation</li>
					<li>Name is byte array</li>
					<li>Temperature as an <code>int</code></li>
					<li>Victorize the name comparison</li>
					<li>Use name normalization</li>
				</ul>
			</li>
		</ul>
		<div style="padding: 100px; text-align: center; font-size: 80px;">
			Thank you!
		</div>
	</div>
	<div class="right" style="text-align: center;">
		<img src="../nawaman.png"  width="300px" alt="NawaMan" style="margin: 80px; margin-top: 0px; margin-bottom: 0px;" />
		<div style="margin-left: 110px;">NawaMan</div>
		<table style="margin-left: 50px; margin-top: 30px; width: 100%; text-align: center; border-collapse: collapse;">
			<tr>
			  <td style="vertical-align: middle; padding: 20px;">
				<div style="display: inline-block; text-align: center;">
				  <img src="../nawaman-contact-qr.png" width="80px" alt="NawaMan contact page - QR" />
				  <br />
				  <a href="https://nawaman.net/contact.html" target="_blank" style="font-size: medium;">contact.html</a>
				</div>
			  </td>
			  <td style="vertical-align: middle; padding: 20px;">
				<div style="display: inline-block; text-align: center;">
				  <img src="../OneBRC-GitHub-qr.png" width="80px" alt="OneBRC GitHub Page - QR" />
				  <br />
				  <a href="https://github.com/NawaMan/OneBRC/" target="_blank" style="font-size: small;">OneBRC</a>
				</div>
			  </td>
			</tr>
		  </table>
		  
	</div>
</ContentPage>
<NavigationBar
	prevLink="./solution-7-name-normalization.html"
/>

<style>
	ul {
		margin: 0px;
	}
	ul li ul {
		font-size: larger;
	}
	.left,
	.right {
		margin: 0px;
		padding: 0px;
		display: inline-block;
		vertical-align: top;
		text-align: left;
	}
	.left {
		width: 60%;
	}
	.right {
		padding: 1em;
		width: 30%;
	}
	img {
		border-radius: 10px;
		border: 2px solid black;
		box-shadow: 0 0 0 2px white;
		border-radius: 10px;
	}
</style>
