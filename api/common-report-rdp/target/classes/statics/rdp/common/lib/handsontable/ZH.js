(function webpackUniversalModuleDefinition(root, factory) {
	if(typeof exports === 'object' && typeof module === 'object')
		module.exports = factory(require("../../handsontable"));
	else if(typeof define === 'function' && define.amd)
		define(["../../handsontable"], factory);
	else {
		var a = typeof exports === 'object' ? factory(require("../../handsontable")) : factory(root["Handsontable"]);
		for(var i in a)(typeof exports === 'object' ? exports : root)[i] = a[i];
	}
})(typeof self !== 'undefined' ? self : this, function(__WEBPACK_EXTERNAL_MODULE_0__) {
	return /******/ (function(modules) { // webpackBootstrap
		/******/ // The module cache
		/******/
		var installedModules = {};
		/******/
		/******/ // The require function
		/******/
		function __webpack_require__(moduleId) {
			/******/
			/******/ // Check if module is in cache
			/******/
			if(installedModules[moduleId]) {
				/******/
				return installedModules[moduleId].exports;
				/******/
			}
			/******/ // Create a new module (and put it into the cache)
			/******/
			var module = installedModules[moduleId] = {
				/******/
				i: moduleId,
				/******/
				l: false,
				/******/
				exports: {}
				/******/
			};
			/******/
			/******/ // Execute the module function
			/******/
			modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
			/******/
			/******/ // Flag the module as loaded
			/******/
			module.l = true;
			/******/
			/******/ // Return the exports of the module
			/******/
			return module.exports;
			/******/
		}
		/******/
		/******/
		/******/ // expose the modules object (__webpack_modules__)
		/******/
		__webpack_require__.m = modules;
		/******/
		/******/ // expose the module cache
		/******/
		__webpack_require__.c = installedModules;
		/******/
		/******/ // define getter function for harmony exports
		/******/
		__webpack_require__.d = function(exports, name, getter) {
			/******/
			if(!__webpack_require__.o(exports, name)) {
				/******/
				Object.defineProperty(exports, name, {
					/******/
					configurable: false,
					/******/
					enumerable: true,
					/******/
					get: getter
					/******/
				});
				/******/
			}
			/******/
		};
		/******/
		/******/ // getDefaultExport function for compatibility with non-harmony modules
		/******/
		__webpack_require__.n = function(module) {
			/******/
			var getter = module && module.__esModule ?
				/******/
				function getDefault() {
					return module['default'];
				} :
				/******/
				function getModuleExports() {
					return module;
				};
			/******/
			__webpack_require__.d(getter, 'a', getter);
			/******/
			return getter;
			/******/
		};
		/******/
		/******/ // Object.prototype.hasOwnProperty.call
		/******/
		__webpack_require__.o = function(object, property) {
			return Object.prototype.hasOwnProperty.call(object, property);
		};
		/******/
		/******/ // __webpack_public_path__
		/******/
		__webpack_require__.p = "";
		/******/
		/******/ // Load entry module and return exports
		/******/
		return __webpack_require__(__webpack_require__.s = 3);
		/******/
	})
	/************************************************************************/
	/******/
	([
		/* 0 */
		/***/
		(function(module, exports) {

			module.exports = __WEBPACK_EXTERNAL_MODULE_0__;

			/***/
		}),
		/* 1 */
		,
		/* 2 */
		,
		/* 3 */
		/***/
		(function(module, exports, __webpack_require__) {

			"use strict";

			exports.__esModule = true;

			var _dictionary;

			var _handsontable = __webpack_require__(0);

			var _handsontable2 = _interopRequireDefault(_handsontable);

			function _interopRequireDefault(obj) {
				return obj && obj.__esModule ? obj : {
					default: obj
				};
			}

			function _defineProperty(obj, key, value) {
				if(key in obj) {
					Object.defineProperty(obj, key, {
						value: value,
						enumerable: true,
						configurable: true,
						writable: true
					});
				} else {
					obj[key] = value;
				}
				return obj;
			}
			/**
			 * @preserve
			 * Authors: Handsoncode
			 * Last updated: Nov 15, 2017
			 *
			 * Description: Definition file for English - United States language-country.
			 */

			var C = _handsontable2.default.languages.dictionaryKeys;

			var dictionary = (_dictionary = {
				languageCode: 'ZH'
			}, _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ROW_ABOVE, '在上面插入行'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ROW_BELOW, '在下面插入行'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_INSERT_LEFT, '在左侧插入列'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_INSERT_RIGHT, '在右侧插入列'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_REMOVE_ROW, ['删除当前行', '删除选中行']), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_REMOVE_COLUMN, ['删除当前列', '删除选中列']), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_UNDO, '撤销'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_REDO, '重做'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_READ_ONLY, '只读'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_CLEAR_COLUMN, '清除列'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT, '对齐'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_LEFT, '居左'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_CENTER, '居中'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_RIGHT, '居右'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_JUSTIFY, '填充'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_TOP, '顶端对齐'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_MIDDLE, '垂直居中'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ALIGNMENT_BOTTOM, '底端对齐'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_FREEZE_COLUMN, '冻结列'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_UNFREEZE_COLUMN, '解冻列'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_BORDERS, '边框'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_BORDERS_TOP, '上边框'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_BORDERS_RIGHT, '右边框'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_BORDERS_BOTTOM, '下边框'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_BORDERS_LEFT, '左边框'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_REMOVE_BORDERS, '删除边框'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_ADD_COMMENT, '添加注释'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_EDIT_COMMENT, '编辑注释'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_REMOVE_COMMENT, '删除注释'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_READ_ONLY_COMMENT, '只读注释'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_MERGE_CELLS, '合并单元格'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_UNMERGE_CELLS, '取消合并'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_COPY, '复制'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_CUT, '剪切'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_NESTED_ROWS_INSERT_CHILD, 'Insert child row'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_NESTED_ROWS_DETACH_CHILD, 'Detach from parent'), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_HIDE_COLUMN, ['隐藏当前单元格', '隐藏选中单元格']), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_SHOW_COLUMN, ['显示当前单元格', '显示选中单元格']), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_HIDE_ROW, ['隐藏当前行', '隐藏选中行']), _defineProperty(_dictionary, C.CONTEXTMENU_ITEMS_SHOW_ROW, ['显示当前行', '显示选中行']), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_NONE, '不存在'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_EMPTY, '空'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_NOT_EMPTY, '非空'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_EQUAL, '等于'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_NOT_EQUAL, '不等于'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_BEGINS_WITH, '开始于'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_ENDS_WITH, '结束于'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_CONTAINS, '包含'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_NOT_CONTAIN, '不包含'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_GREATER_THAN, '大于'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_GREATER_THAN_OR_EQUAL, '大于等于'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_LESS_THAN, '小于'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_LESS_THAN_OR_EQUAL, '小于等于'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_BETWEEN, '在...之间'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_NOT_BETWEEN, '不在...之间'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_AFTER, '之后'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_BEFORE, '之前'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_TODAY, '今天'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_TOMORROW, '明天'), _defineProperty(_dictionary, C.FILTERS_CONDITIONS_YESTERDAY, '昨天'), _defineProperty(_dictionary, C.FILTERS_VALUES_BLANK_CELLS, '空白单元格'), _defineProperty(_dictionary, C.FILTERS_DIVS_FILTER_BY_CONDITION, '按条件过滤'), _defineProperty(_dictionary, C.FILTERS_DIVS_FILTER_BY_VALUE, '按值过滤'), _defineProperty(_dictionary, C.FILTERS_LABELS_CONJUNCTION, '且'), _defineProperty(_dictionary, C.FILTERS_LABELS_DISJUNCTION, '或'), _defineProperty(_dictionary, C.FILTERS_BUTTONS_SELECT_ALL, '全选'), _defineProperty(_dictionary, C.FILTERS_BUTTONS_CLEAR, '清除'), _defineProperty(_dictionary, C.FILTERS_BUTTONS_OK, '确定'), _defineProperty(_dictionary, C.FILTERS_BUTTONS_CANCEL, '取消'), _defineProperty(_dictionary, C.FILTERS_BUTTONS_PLACEHOLDER_SEARCH, '搜索'), _defineProperty(_dictionary, C.FILTERS_BUTTONS_PLACEHOLDER_VALUE, '值'), _defineProperty(_dictionary, C.FILTERS_BUTTONS_PLACEHOLDER_SECOND_VALUE, '第二个值'), _dictionary);

			_handsontable2.default.languages.registerLanguageDictionary(dictionary);

			exports.default = dictionary;

			/***/
		})
		/******/
	])["___"];
});