
var ScrollableTable = {
	init: false,
	_scrollBarWidth : 18,
	set: function(id, width, height, overflowX, center) {
		if (this.init) {
			return;
		}
		if (overflowX == null) {
			overflowX = false;
		}
		if (center == null) {
			center = false;
		}
		var masterTable = document.getElementById(id).children[0]; 

		if (masterTable == null) {
			//alert("Err \n no table ");
			return;
		}
		if (masterTable.tHead == null) {
			//alert("err\n no <THEAD>");
			return;
		}

		if (masterTable.caption != null) {
			masterTable.caption.innerHTML = ""; 
		}

		var thHeight = masterTable.tHead.offsetHeight;
		var tableHeader = masterTable.cloneNode(true);

		tableHeader.id = tableHeader.id + "_H";
		while (tableHeader.tBodies[0].rows.length) {
			tableHeader.tBodies[0].deleteRow(0);
		}
		tableHeader.style.position = "absolute";
		tableHeader.style.left = "0";
		tableHeader.style.top = "0";

		var divHeader = document.createElement("div");
		divHeader.id = "D_" + tableHeader.id;
		divHeader.style.width = width + "px";
		divHeader.style.height = thHeight + "px";
		divHeader.style.overflow = "hidden";
		divHeader.style.position = "relative";
		divHeader.appendChild(tableHeader);

		masterTable.parentNode.insertBefore(divHeader, masterTable);

		var tableBody = masterTable.cloneNode(true);
		tableBody.id = tableBody.id + "_B";
		tableBody.deleteTHead();

		var divBody = document.createElement("div");
		divBody.id = "D_" + tableBody.id;
		divBody.style.width = (width + this._scrollBarWidth) + "px";
		divBody.style.height = height + "px";
		if (overflowX) {
			divBody.style.overflow  = "scroll";
		} else {
			divBody.style.overflowY = "scroll";
			divBody.style.overflowX = "auto";
		}
		if (center) {
			divBody.style.marginLeft  = this._scrollBarWidth + "px";
		}
		divBody.appendChild(tableBody);

		masterTable.parentNode.insertBefore(divBody, masterTable);
		masterTable.parentNode.removeChild(masterTable);

		divBody.onscroll = function() {
			ScrollableTable._onscroll(divHeader.id, divBody.id)
		};

		this.init = true;
	},
	
	_onscroll: function(divHeaderId, divBodyId) {
		var divHeader = document.getElementById(divHeaderId);
		var divBody = document.getElementById(divBodyId);
		divHeader.firstChild.style.left =  "-" + divBody.scrollLeft + "px";
	}
}